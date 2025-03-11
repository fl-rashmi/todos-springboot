import { useState } from "react";
import { useAuth } from "../context/AuthContext";

const Auth = () => {
  const [isLogin, setIsLogin] = useState(true);
  const [input, setInput] = useState({ username: "", password: "" });
  const { login, register } = useAuth();

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (isLogin) {
      await login(input);
    } else {
      const response = await register(input);
      if (response) {
        setIsLogin(true);
        setInput({});
      }
    }
  };

  return (
    <div className="auth-container">
      <h2>{isLogin ? "Login" : "Register"}</h2>
      <form onSubmit={handleSubmit} className="auth-form">
        <div className="input-group">
          <label htmlFor="username">Username</label>
          <input
            type="text"
            id="username"
            value={input.username}
            onChange={(e) => setInput({ ...input, username: e.target.value })}
            required
          />
        </div>
        <div className="input-group">
          <label htmlFor="password">Password</label>
          <input
            type="password"
            id="password"
            value={input.password}
            onChange={(e) => setInput({ ...input, password: e.target.value })}
            required
          />
        </div>
        <button type="submit" className="button">
          {isLogin ? "Login" : "Register"}
        </button>
      </form>
      <p className="auth-switch">
        {isLogin ? "Don't have an account? " : "Already have an account? "}
        <button onClick={() => setIsLogin(!isLogin)} className="link-button">
          {isLogin ? "Register" : "Login"}
        </button>
      </p>
    </div>
  );
};

export default Auth;
