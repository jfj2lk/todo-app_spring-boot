import { Button } from "@/components/ui/button";
import { TodoReducerActions, TodoType } from "@/types/todo";
import { apiRequest } from "@/utils/api";
import { useState } from "react";

const TodoDetail = (props: {
  todo: TodoType;
  todoDispatch: React.Dispatch<TodoReducerActions>;
}) => {
  const [name, setName] = useState<string>(props.todo.name);
  const [desc, setDesc] = useState<string>(props.todo.desc);
  const [priority, setPriority] = useState<number>(props.todo.priority);
  const [dueDate, setDueDate] = useState<string>(props.todo.dueDate);
  const [dueTime, setDueTime] = useState<string>(props.todo.dueTime);

  // Todo更新
  const handleUpdateTodo = async (updateTodoId: number) => {
    const json = await apiRequest<TodoType>(
      `/api/todos/${updateTodoId}`,
      "PATCH",
      {
        name,
        desc,
        priority,
        dueDate,
        dueTime,
      },
    );
    props.todoDispatch({ type: "updated", data: json.data });
  };

  // Todo削除
  const handleDeleteTodo = async (deleteTodoId: number) => {
    const json = await apiRequest<TodoType>(
      `/api/todos/${deleteTodoId}`,
      "DELETE",
    );
    props.todoDispatch({ type: "deleted", id: json.data });
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

          <Button type="submit">更新</Button>
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
