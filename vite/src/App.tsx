import { useState } from "react";
import "./App.css";

function App() {
  const [name, setName] = useState<string>("");

  // GETリクエストをテスト
  const handleGetRequest = async () => {
    const response = await fetch("/api/todos");
    const json = await response.json();
    console.log(json);
  };

  // POSTリクエストをテスト
  const handlePostRequest = async (name: string) => {
    const response = await fetch("/api/todos", {
      method: "POST",
      body: JSON.stringify(name),
    });
    const json = await response.json();
    console.log(json);
  };

  return (
    <div>
      {/* GETリクエスト */}
      <h1>Api Test</h1>
      <form
        onSubmit={(e) => {
          e.preventDefault();
          handleGetRequest();
        }}
      >
        <button>get</button>
      </form>

      {/* POSTリクエスト */}
      <form
        onSubmit={(e) => {
          e.preventDefault();
          handlePostRequest(name);
        }}
      >
        <input
          type="text"
          name="name"
          value={name}
          onChange={(e) => {
            setName(e.target.value);
          }}
        />
        <button>post</button>
      </form>
    </div>
  );
}

export default App;
