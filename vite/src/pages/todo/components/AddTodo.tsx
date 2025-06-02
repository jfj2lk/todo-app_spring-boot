import { TodoType, TodoReducerActions } from "@/types/todo";
import { apiRequest } from "@/utils/api";
import { Plus } from "lucide-react";
import { useState } from "react";

const AddTodo = (props: {
  todos: TodoType[];
  todoDispatch: React.Dispatch<TodoReducerActions>;
}) => {
  const [isAdding, setIsAdding] = useState<boolean>(false);
  const [name, setName] = useState<string>("");
  const [desc, setDesc] = useState<string>("");
  const [priority, setPriority] = useState<number>(4);
  const [dueDate, setDueDate] = useState<string>("");
  const [dueTime, setDueTime] = useState<string>("");

  // Todo追加
  const handleAddTodo = async () => {
    const json = await apiRequest<TodoType>("/api/todos", "POST", {
      name,
      desc,
      priority,
      dueDate,
      dueTime,
    });
    props.todoDispatch({ type: "added", data: json.data });
    setName("");
    setDesc("");
    setIsAdding(false);
  };

  return (
    <div className="flex-1">
      {!isAdding ? (
        <button
          className="mb-2 flex w-full cursor-pointer gap-2 rounded bg-gray-300 px-4 py-2"
          onClick={() => setIsAdding(true)}
        >
          <Plus /> <p>タスクの追加</p>
        </button>
      ) : (
        <form
          onSubmit={(e) => {
            e.preventDefault();
            handleAddTodo();
          }}
          className="mb-4 rounded border bg-white p-4"
        >
          {/* タスク名入力欄 */}
          <input
            type="text"
            value={name}
            onChange={(e) => {
              setName(e.target.value);
            }}
            placeholder="タスク名"
            className="mb-2 w-full rounded border p-2"
          />

          {/* 説明入力欄 */}
          <input
            type="text"
            value={desc}
            onChange={(e) => {
              setDesc(e.target.value);
            }}
            placeholder="説明"
            className="mb-2 w-full rounded border p-2"
          />
          <div className="flex gap-2.5">
            {/* 優先度入力欄 */}
            <input
              type="number"
              min={1}
              max={4}
              value={priority}
              onChange={(e) => {
                setPriority(parseInt(e.target.value));
              }}
              placeholder="優先度"
              className="rounded border p-2"
            />

            {/* 期限日付入力欄 */}
            <input
              type="date"
              value={dueDate}
              onChange={(e) => {
                setDueDate(e.target.value);
              }}
              placeholder="期限日付"
              className="rounded border p-2"
            />

            {/* 期限時刻入力欄 */}
            <input
              type="time"
              value={dueTime}
              onChange={(e) => {
                setDueTime(e.target.value);
              }}
              placeholder="期限時刻"
              className="rounded border p-2"
            />
          </div>

          <div className="flex justify-end space-x-2">
            <button
              className="rounded bg-gray-100 px-4 py-2"
              onClick={() => setIsAdding(false)}
            >
              キャンセル
            </button>
            <button className="rounded bg-black px-4 py-2 text-white">
              タスクを追加
            </button>
          </div>
        </form>
      )}
    </div>
  );
};

export default AddTodo;
