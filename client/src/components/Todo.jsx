import { useEffect, useState } from "react";
import toast from "react-hot-toast";
import { useAuth } from "../context/AuthContext";

const TodoApp = () => {
  const [todos, setTodos] = useState([]);
  const [input, setInput] = useState({ title: "", description: "" });
  const [editingId, setEditingId] = useState(null);
  const { token, logout } = useAuth();

  const fetchAllTodos = async () => {
    try {
      const response = await fetch("http://localhost:8080/todo", {
        headers: { Authorization: `Bearer ${token}` },
      });
      const { data } = await response.json();
      setTodos(data);
    } catch (error) {
      console.error("Error fetching todos:", error);
    }
  };

  const createTodo = async (newTodo) => {
    try {
      const response = await fetch("http://localhost:8080/todo", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(newTodo),
      });

      const { status, message } = await response.json();

      status === "success" && fetchAllTodos();
      toast[status](message);
    } catch (error) {
      console.error("Error creating todo:", error);
      toast.error("Failed to create todo");
    }
  };

  const updateTodo = async (updatedTodo) => {
    try {
      const response = await fetch(`http://localhost:8080/todo/${editingId}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(updatedTodo),
      });

      const { status, message } = await response.json();

      status === "success" && fetchAllTodos();
      toast[status](message);
    } catch (error) {
      console.error("Error updating todo:", error);
    }
  };

  const handleDelete = async (id) => {
    try {
      const response = await fetch(`http://localhost:8080/todo/${id}`, {
        method: "DELETE",
        headers: { Authorization: `Bearer ${token}` },
      });

      const { status, message } = await response.json();

      status === "success" && fetchAllTodos();
      toast[status](message);
    } catch (error) {
      console.error("Error deleting todo:", error);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!input.title.trim() || !input.description.trim()) return;

    const todoData = {
      title: input.title,
      description: input.description,
    };

    if (editingId !== null) {
      await updateTodo(todoData);
      setEditingId(null);
    } else {
      await createTodo(todoData);
    }

    setInput({ title: "", description: "" });
  };

  const handleEdit = (todo) => {
    setInput({ title: todo.title, description: todo.description });
    setEditingId(todo.id);
  };

  useEffect(() => {
    fetchAllTodos();
  }, []);

  return (
    <div className="container">
      <div className="header">
        <div className="h5">My Todo List</div>
        <div className="logout" onClick={logout}>
          Logout →
        </div>
      </div>
      <form className="todo-form" onSubmit={handleSubmit}>
        <div className="input-group">
          <label htmlFor="title">Title</label>
          <input
            type="text"
            id="title"
            value={input.title}
            onChange={(e) => setInput({ ...input, title: e.target.value })}
            placeholder="Enter todo title"
            maxLength={100}
          />
        </div>

        <div className="input-group">
          <label htmlFor="description">Description</label>
          <textarea
            id="description"
            value={input.description}
            onChange={(e) =>
              setInput({ ...input, description: e.target.value })
            }
            placeholder="Enter todo description"
            maxLength={500}
            rows={3}
          />
        </div>

        <button type="submit" className="button">
          {editingId !== null ? "Update Todo" : "Add Todo"}
        </button>
      </form>

      <div className="todo-list">
        {todos.length === 0 && <p className="notfound">No todos found</p>}
        {todos.map((todo, index) => (
          <div key={index} className="todo-item">
            <h3>{todo.title}</h3>
            <p>{todo.description}</p>
            <div className="todo-actions">
              <button
                className="button edit-button"
                onClick={() => handleEdit(todo)}
              >
                Edit
              </button>
              <button
                className="button delete-button"
                onClick={() => handleDelete(todo.id)}
              >
                Delete
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default TodoApp;
