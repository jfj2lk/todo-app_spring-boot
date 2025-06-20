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
import { Plus } from "lucide-react";

import { useState } from "react";

const TodoDetail = (props: {
  projectId: number;
  todo: TodoType;
  todoDispatch: React.Dispatch<TodoReducerActions>;
  labels: LabelType[];
  setSelectedTodo: React.Dispatch<React.SetStateAction<TodoType | null>>;
}) => {
  const labelIdsFromTodo: number[] = props.todo.todoLabels.map(
    (todoLabel) => todoLabel.labelId,
  );

  const [name, setName] = useState<string>(props.todo.name);
  const [desc, setDesc] = useState<string>(props.todo.desc);
  const [priority, setPriority] = useState<number>(props.todo.priority);
  const [dueDate, setDueDate] = useState<string>(props.todo.dueDate);
  const [dueTime, setDueTime] = useState<string>(props.todo.dueTime);
  const [labelIds, setLabelIds] = useState<number[]>(labelIdsFromTodo);

  // Todo更新
  const handleUpdateTodo = (todoId: number) => {
    axios
      .patch<ApiResponse<TodoType>>(
        `/api/projects/${props.projectId}/todos/${todoId}`,
        {
          name,
          desc,
          priority,
          dueDate,
          dueTime,
          labelIds,
        },
      )
      .then((response) => {
        props.todoDispatch({ type: "updated", data: response.data.data });
        setName("");
        setDesc("");
        setLabelIds([]);
        props.setSelectedTodo(null);
      });
  };

  // Todo削除
  const handleDeleteTodo = (todoId: number) => {
    axios
      .delete<
        ApiResponse<TodoType>
      >(`/api/projects/${props.projectId}/todos/${todoId}`)
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
              value={desc}
              onChange={(e) => {
                setDesc(e.target.value);
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
            <PopoverTrigger className="self-start" asChild>
              <Button type="button" variant={"outline"}>
                <Plus />
                <span>ラベル</span>
              </Button>
            </PopoverTrigger>
            <PopoverContent className="w-auto">
              <ul className="flex flex-col gap-3">
                {props.labels.map((label) => (
                  <li key={label.id}>
                    <Label>
                      <Checkbox
                        name="labels"
                        value={label.id}
                        checked={labelIds.includes(label.id)}
                        onCheckedChange={(checked) => {
                          checked
                            ? setLabelIds([...labelIds, label.id])
                            : setLabelIds(
                                labelIds.filter(
                                  (labelId) => labelId !== label.id,
                                ),
                              );
                        }}
                      />
                      <span>{label.name}</span>
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
