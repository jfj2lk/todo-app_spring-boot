import { Todo, TodoReducerActions } from "@/types/todo";
import { apiRequest } from "@/utils/api";
import React, { useEffect, useRef, useState } from "react";

const UpdateTodo = (props: {
  todo: Todo;
  todos: Todo[];
  todoDispatch: React.Dispatch<TodoReducerActions>;
  editingId: number | null;
  setEditingId: React.Dispatch<React.SetStateAction<number | null>>;
}) => {
  const [name, setName] = useState<string>(props.todo.name);
  const [desc, setDesc] = useState<string>(props.todo.desc);
  const [priority, setPriority] = useState<number>(props.todo.priority);
  const [dueDate, setDueDate] = useState<string>(props.todo.dueDate);
  const [dueTime, setDueTime] = useState<string>(props.todo.dueTime);
  // 編集中の入力欄の要素
  const inputRef = useRef<HTMLInputElement | null>(null);

  // Todo更新
  const handleUpdateTodo = async (updateTodoId: number) => {
    const json = await apiRequest<Todo>(`/api/todos/${updateTodoId}`, "PATCH", {
      name,
      desc,
      priority,
      dueDate,
      dueTime,
    });
    props.todoDispatch({ type: "updated", data: json.data });
    props.setEditingId(null);
  };

  // Todoが編集中になった場合に、入力欄にフォーカスする
  useEffect(() => {
    if (inputRef.current) {
      inputRef.current.focus();
    }
  }, [props.editingId]);

  return (
    <div>
      <form
        onSubmit={(e) => {
          e.preventDefault();
          handleUpdateTodo(props.todo.id);
        }}
      >
        {/* 名前入力欄 */}
        <input
          type="text"
          value={name}
          onChange={(e) => {
            setName(e.target.value);
          }}
          ref={inputRef}
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

        <button>更新</button>
      </form>
    </div>
  );
};

export default UpdateTodo;
