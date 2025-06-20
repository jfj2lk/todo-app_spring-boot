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

const AddTodo = (props: {
  projectId: number;
  todos: TodoType[];
  todoDispatch: React.Dispatch<TodoReducerActions>;
  labels: LabelType[];
}) => {
  const [isAdding, setIsAdding] = useState<boolean>(false);
  const [name, setName] = useState<string>("");
  const [desc, setDesc] = useState<string>("");
  const [priority, setPriority] = useState<number>(4);
  const [dueDate, setDueDate] = useState<string>("");
  const [dueTime, setDueTime] = useState<string>("");
  const [labelIds, setLabelIds] = useState<number[]>([]);

  // Todo追加
  const handleAddTodo = () => {
    axios
      .post<ApiResponse<TodoType>>(`/api/projects/${props.projectId}/todos`, {
        name,
        desc,
        priority,
        dueDate,
        dueTime,
        labelIds,
      })
      .then((response) => {
        props.todoDispatch({ type: "added", data: response.data.data });
        setName("");
        setDesc("");
        setLabelIds([]);
        setIsAdding(false);
      });
  };

  return (
    <div className="mb-5 flex-1">
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

            {/* ラベル入力欄 */}
            <Popover>
              <PopoverTrigger asChild>
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
          </div>

          <div className="mt-2 flex justify-end space-x-2">
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
