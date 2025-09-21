import { Button } from "@/components/ui/button";
import { Checkbox } from "@/components/ui/checkbox";
import { Label } from "@/components/ui/label";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import { ApiResponse } from "@/types/api";
import { LabelType } from "@/types/label";
import { TodoReducerActions, TodoType } from "@/types/todo";
import axios from "axios";
import { Pencil, Tag } from "lucide-react";

import { useState } from "react";

const TodoDetail = (props: {
  todo: TodoType;
  todoDispatch: React.Dispatch<TodoReducerActions>;
  labels: LabelType[];
  setSelectedTodo: React.Dispatch<React.SetStateAction<TodoType | null>>;
}) => {
  // Todoに関連付いた全てのラベルのIDを取得
  const todoLabelIdsData: number[] = props.todo.todoLabels.map(
    (todoLabel) => todoLabel.labelId,
  );
  const [todoLabelIds, setTodoLabelIds] = useState<number[]>(todoLabelIdsData);

  // Todoに関連付いた全てのラベルを取得
  const todoLabels: LabelType[] = props.labels.filter((label) =>
    todoLabelIds.includes(label.id),
  );

  const [name, setName] = useState<string>(props.todo.name);
  const [description, setDescription] = useState<string>(
    props.todo.description,
  );
  const [priority, setPriority] = useState<number>(props.todo.priority);
  const [dueDate, setDueDate] = useState<string>(props.todo.dueDate);
  const [dueTime, setDueTime] = useState<string>(props.todo.dueTime);

  // Todo更新
  const handleUpdateTodo = (todoId: number) => {
    axios
      .patch<ApiResponse<TodoType>>(`/api/todos/${todoId}`, {
        name,
        description,
        priority,
        dueDate,
        dueTime,
        labelIds: todoLabelIds,
      })
      .then((response) => {
        props.todoDispatch({ type: "updated", data: response.data.data });
        setName("");
        setDescription("");
        setTodoLabelIds([]);
        props.setSelectedTodo(null);
      });
  };

  // Todo削除
  const handleDeleteTodo = (todoId: number) => {
    axios
      .delete<ApiResponse<TodoType>>(`/api/todos/${todoId}`)
      .then((response) => {
        props.todoDispatch({ type: "deleted", id: response.data.data });
      });
  };

  return (
    <div className="flex-1 px-5 shadow">
      <div className="m-5 flex flex-col gap-2.5">
        <form
          onSubmit={(e) => {
            e.preventDefault();
            handleUpdateTodo(props.todo.id);
          }}
          className="flex flex-col gap-2.5"
        >
          <div className="flex flex-col rounded-md border border-gray-300 p-5">
            {/* 名前入力欄 */}
            <input
              type="text"
              value={name}
              onChange={(e) => {
                setName(e.target.value);
              }}
              className="mb-2 text-xl font-bold"
            />
            {/* 詳細入力欄 */}
            <input
              type="text"
              value={description}
              onChange={(e) => {
                setDescription(e.target.value);
              }}
            />
          </div>

          {/* 優先度入力欄 */}
          <input
            type="number"
            min={1}
            max={4}
            value={priority}
            onChange={(e) => {
              setPriority(parseInt(e.target.value));
            }}
            className="rounded-md border border-gray-300 px-5 py-2"
          />

          {/* 期限日付入力欄 */}
          <input
            type="date"
            value={dueDate}
            onChange={(e) => {
              setDueDate(e.target.value);
            }}
            className="rounded-md border border-gray-300 px-5 py-2"
          />

          {/* 期限時刻入力欄 */}
          <input
            type="time"
            value={dueTime}
            onChange={(e) => {
              setDueTime(e.target.value);
            }}
            className="rounded-md border border-gray-300 px-5 py-2"
          />

          {/* ラベル入力欄 */}
          <Popover>
            <PopoverTrigger className="text-md flex flex-col gap-2.5 rounded-md border px-5 py-2.5">
              <div className="flex justify-between">
                <span>ラベル</span>
                <Pencil size={16} />
              </div>

              {/* ラベル一覧表示 */}
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
            </PopoverTrigger>
            <PopoverContent className="min-w-[15rem]">
              <ul className="flex flex-col gap-3">
                {props.labels.map((label) => (
                  <li key={label.id}>
                    <Label className="flex justify-between">
                      <span>{label.name}</span>
                      <Checkbox
                        name="labels"
                        value={label.id}
                        checked={todoLabelIds.includes(label.id)}
                        onCheckedChange={(checked) => {
                          if (checked) {
                            setTodoLabelIds([...todoLabelIds, label.id]);
                          } else {
                            setTodoLabelIds(
                              todoLabelIds.filter(
                                (labelId) => labelId !== label.id,
                              ),
                            );
                          }
                        }}
                      />
                    </Label>
                  </li>
                ))}
              </ul>
            </PopoverContent>
          </Popover>

          <Button type="submit" className="mt-2">
            更新
          </Button>
        </form>

        {/* Todo削除フォーム */}
        <form
          onSubmit={(e) => {
            e.preventDefault();
            handleDeleteTodo(props.todo.id);
          }}
        >
          <Button
            type="submit"
            variant={"outline"}
            className="w-full border-red-500 bg-red-200 text-red-500"
          >
            削除
          </Button>
        </form>
      </div>
    </div>
  );
};

export default TodoDetail;
