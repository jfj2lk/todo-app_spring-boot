import { Todo, TodoReducerActions } from "@/types/todo";
import { apiRequest } from "@/utils/api";
import { useState } from "react";

const AddTodo = (props: {
  todos: Todo[];
  todoDispatch: React.Dispatch<TodoReducerActions>;
}) => {
  const [todoName, setTodoName] = useState<string>("");
  const [todoDesc, setTodoDesc] = useState<string>("");

  // Todo追加
  const handleAddTodo = async () => {
    const json = await apiRequest<Todo>("/api/todos", "POST", {
      name: todoName,
      desc: todoDesc,
    });
    props.todoDispatch({ type: "added", data: json.data });
    setTodoName("");
    setTodoDesc("");
  };

  return (
    <div>
      <form
        onSubmit={(e) => {
          e.preventDefault();
          handleAddTodo();
        }}
      >
        {/* 名前入力欄 */}
        <input
          type="text"
          value={todoName}
          onChange={(e) => {
            setTodoName(e.target.value);
          }}
        />

        {/* 詳細入力欄 */}
        <input
          type="text"
          value={todoDesc}
          onChange={(e) => {
            setTodoDesc(e.target.value);
          }}
        />

        <button>追加</button>
      </form>
    </div>
  );
};

export default AddTodo;
