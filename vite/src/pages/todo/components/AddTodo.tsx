import { Todo, TodoReducerActions } from "@/types/todo";
import { apiRequest } from "@/utils/api";
import { useState } from "react";

const AddTodo = (props: {
  todos: Todo[];
  todoDispatch: React.Dispatch<TodoReducerActions>;
}) => {
  const [todoName, setTodoName] = useState<string>("");
  const [todoDesc, setTodoDesc] = useState<string>("");
  const [todoPriority, setTodoPriority] = useState<number>(4);
  const [todoDueDate, setTodoDueDate] = useState<string>("");
  const [todoDueTime, setTodoDueTime] = useState<string>("");

  // Todo追加
  const handleAddTodo = async () => {
    const json = await apiRequest<Todo>("/api/todos", "POST", {
      name: todoName,
      desc: todoDesc,
      priority: todoPriority,
      dueDate: todoDueDate,
      dueTime: todoDueTime,
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

        {/* 優先度入力欄 */}
        <input
          type="number"
          min={1}
          max={4}
          value={todoPriority}
          onChange={(e) => {
            setTodoPriority(parseInt(e.target.value));
          }}
        />

        {/* 期限日付入力欄 */}
        <input
          type="date"
          value={todoDueDate}
          onChange={(e) => {
            setTodoDueDate(e.target.value);
          }}
        />

        {/* 期限時刻入力欄 */}
        <input
          type="time"
          value={todoDueTime}
          onChange={(e) => {
            setTodoDueTime(e.target.value);
          }}
        />

        <button>追加</button>
      </form>
    </div>
  );
};

export default AddTodo;
