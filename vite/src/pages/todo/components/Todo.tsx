import { TodoReducerActions, TodoType } from "@/types/todo";
import { apiRequest } from "@/utils/api";
import { Circle } from "lucide-react";
import React from "react";

const Todo = (props: {
  todo: TodoType;
  todos: TodoType[];
  todoDispatch: React.Dispatch<TodoReducerActions>;
  selectedTodo: TodoType | null;
  setSelectedTodo: React.Dispatch<React.SetStateAction<TodoType | null>>;
}) => {
  // Todoの完了・未完了状態を変更するAPIリクエストを送信
  const toggleComplete = async (todoId: number) => {
    const json = await apiRequest<TodoType>(
      `/api/todos/${todoId}/toggleComplete`,
      "PATCH",
    );
    props.todoDispatch({ type: "updated", data: json.data });
  };

  return (
    <div
      className={`flex cursor-pointer items-center gap-2 rounded-md border px-4 py-2 ${props.selectedTodo?.id === props.todo.id ? "border-blue-300 bg-blue-100" : ""}`}
    >
      <button onClick={() => toggleComplete(props.todo.id)}>
        <Circle className="h-4 w-4" />
      </button>
      <button
        onClick={() => props.setSelectedTodo(props.todo)}
        className="w-full text-left"
      >
        {props.todo.name}
      </button>
    </div>
  );
};

export default Todo;
