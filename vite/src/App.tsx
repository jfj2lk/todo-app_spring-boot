import { useState } from "react";
import "./App.css";

function App() {
  const [name, setName] = useState<string>("");
  const [desc, setDesc] = useState<string>("");

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
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ name, desc }),
    });
    const json = await response.json();

    if (!response.ok) {
      json.forEach((error: any) => {
        console.log(error.defaultMessage);
      });
    } else {
      console.log(json);
    }
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
        <div>
          <label>
            <span>name: </span>
            <input
              type="text"
              name="name"
              value={name}
              onChange={(e) => {
                setName(e.target.value);
              }}
            />
          </label>
        </div>
        <div>
          <label>
            <span>desc:</span>
            <input
              type="text"
              name="desc"
              value={desc}
              onChange={(e) => {
                setDesc(e.target.value);
              }}
            />
          </label>
        </div>
        <button>post</button>
      </form>
    </div>
  );
}

export default App;
