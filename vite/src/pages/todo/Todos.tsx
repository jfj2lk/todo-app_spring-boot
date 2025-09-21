import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/components/ui/popover";
import { todosReducer } from "@/state/todosReducer";
import { useAppSelector } from "@/store";
import { labelSelectors } from "@/store/label-store";
import { ApiResponse } from "@/types/api";
import { TodoType } from "@/types/todo";
import axios from "axios";
import { Ellipsis } from "lucide-react";
import { useEffect, useReducer, useState } from "react";
import { useSearchParams } from "react-router-dom";
import AddTodo from "./components/AddTodo";
import TodoDetail from "./components/TodoDetail";
import TodoList from "./components/TodoList";

const Todos = () => {
  const [searchParams] = useSearchParams();
  const projectId = searchParams.get("projectId")
    ? Number(searchParams.get("projectId"))
    : null;
  const labelId = searchParams.get("labelId")
    ? Number(searchParams.get("labelId"))
    : null;

  const [todos, todoDispatch] = useReducer(todosReducer, []);
  const labels = useAppSelector(labelSelectors.selectAll);
  // 選択中のTodoのID
  const [selectedTodo, setSelectedTodo] = useState<TodoType | null>(null);
  // 並び替え要素
  const [sortKey, setSortKey] = useState<string>("createdAt");
  // 並び変え順
  const [sortOrder, setSortOrder] = useState<string>("ascending");
  // Todos取得
  useEffect(() => {
    const fetchInitialData = () => {
      let queryParameters = "";
      if (projectId) {
        queryParameters = `?projectId=${projectId}`;
      } else if (labelId) {
        queryParameters = `?labelId=${labelId}`;
      }

      axios
        .get<ApiResponse<TodoType[]>>(`/api/todos${queryParameters}`)
        .then((response) => {
          todoDispatch({ type: "initialized", data: response.data.data });
        });
    };
    fetchInitialData();
  }, [projectId, labelId]);

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
    <div className="flex min-h-screen">
      {/* コンテンツ */}
      <div className="flex w-[60%] flex-col">
        <div className="flex items-center justify-between px-7 py-4 text-2xl font-bold shadow">
          <h2>リスト名</h2>
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

        {/* インラインコンテンツ */}
        <div className="flex-1 px-7 py-5">
          {/* Todo追加欄 */}
          <AddTodo
            projectId={projectId}
            todos={todos}
            todoDispatch={todoDispatch}
            labels={labels}
          />

          {/* Todo一覧 */}
          <TodoList
            todos={todos}
            todoDispatch={todoDispatch}
            selectedTodo={selectedTodo}
            setSelectedTodo={setSelectedTodo}
            labels={labels}
          />
        </div>
      </div>

      {/* Todo詳細 */}
      {selectedTodo && (
        <TodoDetail
          key={selectedTodo.id}
          todo={selectedTodo}
          setSelectedTodo={setSelectedTodo}
          todoDispatch={todoDispatch}
          labels={labels}
        />
      )}
    </div>
  );
};

export default Todos;
