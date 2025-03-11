import { createContext, useState, useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-hot-toast";

const AuthContext = createContext(null);

export const AuthProvider = ({ children }) => {
  const [token, setToken] = useState(localStorage.getItem("token"));
  const navigate = useNavigate();

  useEffect(() => {
    if (token) {
      localStorage.setItem("token", token);
      navigate("/");
    } else {
      localStorage.removeItem("token");
    }
  }, [token]);

  const login = async (input) => {
    try {
      const response = await fetch("http://localhost:8080/user/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(input),
      });

      const { status, message, data } = await response.json();

      status === "success" && setToken(data);
      toast[status](message);
    } catch (error) {
      console.error("Registration error:", error);
      toast.error("Registration failed");
    }
  };

  const register = async (input) => {
    try {
      const response = await fetch("http://localhost:8080/user/register", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(input),
      });

      const { status, message } = await response.json();

      toast[status](message);
      return status === "success" ? true : false;
    } catch (error) {
      console.error("Registration error:", error);
      toast.error("Registration failed");
    }
  };

  const logout = () => setToken(null);

  return (
    <AuthContext.Provider value={{ token, login, register, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
