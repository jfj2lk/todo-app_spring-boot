import { Todo, TodoReducerActions } from "@/types/todo";
import { apiRequest } from "@/utils/api";
import { useState } from "react";

const AddTodo = (props: {
  todos: Todo[];
  todoDispatch: React.Dispatch<TodoReducerActions>;
}) => {
  const [name, setName] = useState<string>("");
  const [desc, setDesc] = useState<string>("");
  const [priority, setPriority] = useState<number>(4);
  const [dueDate, setDueDate] = useState<string>("");
  const [dueTime, setDueTime] = useState<string>("");

  // Todo追加
  const handleAddTodo = async () => {
    const json = await apiRequest<Todo>("/api/todos", "POST", {
      name,
      desc,
      priority,
      dueDate,
      dueTime,
    });
    props.todoDispatch({ type: "added", data: json.data });
    setName("");
    setDesc("");
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
          value={name}
          onChange={(e) => {
            setName(e.target.value);
          }}
        />

        {/* 詳細入力欄 */}
        <input
          type="text"
          value={desc}
          onChange={(e) => {
            setDesc(e.target.value);
          }}
        />

        {/* 優先度入力欄 */}
        <input
          type="number"
          min={1}
          max={4}
          value={priority}
          onChange={(e) => {
            setPriority(parseInt(e.target.value));
          }}
        />

        {/* 期限日付入力欄 */}
        <input
          type="date"
          value={dueDate}
          onChange={(e) => {
            setDueDate(e.target.value);
          }}
        />

        {/* 期限時刻入力欄 */}
        <input
          type="time"
          value={dueTime}
          onChange={(e) => {
            setDueTime(e.target.value);
          }}
        />

        <button>追加</button>
      </form>
    </div>
  );
};

export default AddTodo;
