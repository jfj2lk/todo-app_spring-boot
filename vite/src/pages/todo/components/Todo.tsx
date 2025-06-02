import { TodoReducerActions, TodoType } from "@/types/todo";
import { apiRequest } from "@/utils/api";
import { Circle } from "lucide-react";
import React, { useState } from "react";
import UpdateTodo from "./UpdateTodo";
import DeleteTodo from "./DeleteTodo";

const Todo = (props: {
  todo: TodoType;
  todos: TodoType[];
  todoDispatch: React.Dispatch<TodoReducerActions>;
  selectedTodoId: number | null;
  setSelectedTodoId: React.Dispatch<React.SetStateAction<number | null>>;
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
    <>
      {props.todo.id !== props.selectedTodoId ? (
        // Todo内容表示
        <div className="flex items-center gap-2">
          <button onClick={() => toggleComplete(props.todo.id)}>
            <Circle className="h-4 w-4" />
          </button>
          <button onClick={() => props.setSelectedTodoId(props.todo.id)}>
            {props.todo.name}
          </button>
        </div>
      ) : (
        <div className="flex">
          {/* Todo更新欄 */}
          <UpdateTodo
            todo={props.todo}
            todos={props.todos}
            todoDispatch={props.todoDispatch}
            editingId={props.selectedTodoId}
            setEditingId={props.setSelectedTodoId}
          />

          <DeleteTodo
            todo={props.todo}
            todos={props.todos}
            todoDispatch={props.todoDispatch}
          />
        </div>
      )}
    </>
  );
};

export default Todo;
