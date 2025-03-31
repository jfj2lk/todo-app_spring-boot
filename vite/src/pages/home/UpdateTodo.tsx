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
  const [todoName, setTodoName] = useState<string>(props.todo.name);
  const [todoDesc, setTodoDesc] = useState<string>(props.todo.desc);
  // 編集中の入力欄の要素
  const inputRef = useRef<HTMLInputElement | null>(null);

  // Todo更新
  const handleUpdateTodo = async (updateTodoId: number) => {
    const json = await apiRequest<Todo>(`/api/todos/${updateTodoId}`, "PATCH", {
      todo: { name: todoName, desc: todoDesc },
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
          value={todoName}
          onChange={(e) => {
            setTodoName(e.target.value);
          }}
          ref={inputRef}
        />

        {/* 詳細入力欄 */}
        <input
          type="text"
          value={todoDesc}
          onChange={(e) => {
            setTodoDesc(e.target.value);
          }}
        />

        <button>更新</button>
      </form>
    </div>
  );
};

export default UpdateTodo;
