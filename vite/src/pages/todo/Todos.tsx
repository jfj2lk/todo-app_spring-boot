import { todosReducer } from "@/state/todosReducer";
import { TodoType } from "@/types/todo";
import { apiRequest } from "@/utils/api";
import { useEffect, useReducer, useState } from "react";
import AddTodo from "./components/AddTodo";
import TodoList from "./components/TodoList";
import TodoDetail from "./components/TodoDetail";
import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import { Ellipsis } from "lucide-react";

const Todos = () => {
  const [todos, todoDispatch] = useReducer(todosReducer, []);
  // 選択中のTodoのID
  const [selectedTodo, setSelectedTodo] = useState<TodoType | null>(null);
  // 並び替え要素
  const [sortKey, setSortKey] = useState<string>("createdAt");
  // 並び変え順
  const [sortOrder, setSortOrder] = useState<string>("ascending");
  // Todos取得
  useEffect(() => {
    (async () => {
      const json = await apiRequest<TodoType[]>("/api/todos");
      todoDispatch({ type: "initialized", data: json.data });
    })();
  }, []);

  useEffect(() => {
    // Todosの要素を、並び替え要素と並び変え順の値で並び替える
    const sortedTodos = [...todos].sort((a, b) => {
      const valA = a[sortKey as keyof TodoType];
      const valB = b[sortKey as keyof TodoType];
      if (valA < valB) return sortOrder === "ascending" ? -1 : 1;
      if (valA > valB) return sortOrder === "ascending" ? 1 : -1;
      return 0;
    });
    todoDispatch({ type: "initialized", data: sortedTodos });
  }, [sortKey, sortOrder]);

  return (
    <div className="flex min-h-screen flex-col">
      <h2 className="px-7 py-4 text-2xl font-bold shadow">リスト名</h2>
      <div className="flex flex-1 px-7 py-5">
        <div className="w-[60%] pr-7">
          <div className="flex justify-between gap-4 pr-2">
            {/* Todo追加欄 */}
            <AddTodo todos={todos} todoDispatch={todoDispatch} />
            <Popover>
              <PopoverTrigger asChild>
                <Ellipsis />
              </PopoverTrigger>
              <PopoverContent className="w-60">
                {/* 並び替えメニュー */}
                <div className="flex flex-col gap-2">
                  <div className="flex justify-between">
                    <p>並び替え</p>
                    <select
                      name="sort"
                      value={sortKey}
                      onChange={(e) => {
                        setSortKey(e.target.value);
                      }}
                      className="rounded border-2 border-gray-200 p-1"
                    >
                      <option value="createdAt">作成日</option>
                      <option value="dueDateTime">期限</option>
                      <option value="priority">優先度</option>
                    </select>
                  </div>
                  <div className="flex justify-between">
                    <p>順序</p>
                    <select
                      name="order"
                      value={sortOrder}
                      onChange={(e) => {
                        setSortOrder(e.target.value);
                      }}
                      className="rounded border-2 border-gray-200 p-1"
                    >
                      <option value="ascending">昇順</option>
                      <option value="descending">降順</option>
                    </select>
                  </div>
                </div>
              </PopoverContent>
            </Popover>
          </div>
          {/* Todo一覧 */}
          <TodoList
            todos={todos}
            todoDispatch={todoDispatch}
            selectedTodo={selectedTodo}
            setSelectedTodo={setSelectedTodo}
          />
        </div>
        {/* Todo詳細 */}
        {selectedTodo && (
          <TodoDetail
            key={selectedTodo.id}
            todo={selectedTodo}
            todoDispatch={todoDispatch}
          />
        )}
      </div>
    </div>
  );
};

export default Todos;
