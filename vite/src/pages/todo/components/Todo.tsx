import { LabelType } from "@/types/label";
import { TodoReducerActions, TodoType } from "@/types/todo";
import { apiRequest } from "@/utils/api";
import { Circle, Flag, Tag } from "lucide-react";
import React from "react";

const Todo = (props: {
  todo: TodoType;
  todos: TodoType[];
  todoDispatch: React.Dispatch<TodoReducerActions>;
  selectedTodo: TodoType | null;
  setSelectedTodo: React.Dispatch<React.SetStateAction<TodoType | null>>;
  labels: LabelType[];
}) => {
  // Todoの完了・未完了状態を変更するAPIリクエストを送信
  const toggleComplete = async (todoId: number) => {
    const completeUrl = props.todo.isCompleted ? "incomplete" : "complete";
    const json = await apiRequest<TodoType>(
      `/api/todos/${todoId}/${completeUrl}`,
      "PATCH",
    );
    console.log(json);
    props.todoDispatch({ type: "updated", data: json.data });
  };

  // 優先度の色
  let priorityColor;
  switch (props.todo.priority) {
    case 1: {
      priorityColor = "red";
      break;
    }
    case 2: {
      priorityColor = "orange";
      break;
    }
    case 3: {
      priorityColor = "blue";
      break;
    }
  }

  // Todoに関連付いた全てのラベルのIDを取得
  const todoLabelIds: number[] = props.todo.todoLabels.map(
    (todoLabel) => todoLabel.labelId,
  );
  // Todoに関連付いた全てのラベルを取得
  const todoLabels: LabelType[] = props.labels.filter((label) =>
    todoLabelIds.includes(label.id),
  );

  return (
    <div
      className={`flex cursor-pointer items-center gap-2 rounded-md border px-4 py-2 ${props.selectedTodo?.id === props.todo.id ? "border-blue-300 bg-blue-100" : ""}`}
    >
      <button onClick={() => toggleComplete(props.todo.id)}>
        <Circle className="h-4 w-4" />
      </button>
      <button
        onClick={() => props.setSelectedTodo(props.todo)}
        className="flex-1 cursor-pointer truncate text-left"
      >
        <div className="flex items-center gap-5">
          {/* Todo名 */}
          <div className="max-w-[17.5rem] truncate">{props.todo.name}</div>

          <div className="flex items-center gap-2.5">
            {/* 優先度 */}
            <div className={`rounded-md border px-1 py-0.5`}>
              <Flag size={16} color={priorityColor} />
            </div>

            {/* ラベル */}
            <ul className="flex gap-2.5">
              {todoLabels.map((label) => (
                <li
                  key={label.id}
                  className="flex items-center gap-1 rounded-md border px-1 py-0.5 text-sm text-gray-600"
                >
                  <Tag size={16} />
                  <div>{label.name}</div>
                </li>
              ))}
            </ul>
          </div>
        </div>
      </button>
    </div>
  );
};

export default Todo;
