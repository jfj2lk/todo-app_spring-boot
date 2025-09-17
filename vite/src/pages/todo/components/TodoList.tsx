import { LabelType } from "@/types/label";
import { TodoReducerActions, TodoType } from "@/types/todo";
import Todo from "./Todo";

const TodoList = (props: {
  projectId: number;
  todos: TodoType[];
  todoDispatch: React.Dispatch<TodoReducerActions>;
  selectedTodo: TodoType | null;
  setSelectedTodo: React.Dispatch<React.SetStateAction<TodoType | null>>;
  labels: LabelType[];
}) => {
  // 未完了状態のTodos
  const inCompleteTodos = props.todos.filter((todo) => !todo.isCompleted);
  // 完了状態のTodos
  const completedTodos = props.todos.filter((todo) => todo.isCompleted);

  return (
    <div className="flex flex-col gap-4">
      <h2 className="font-semibold">未完了</h2>
      <ul className="flex flex-col gap-2">
        {inCompleteTodos.map((todo) => (
          <li key={todo.id}>
            <Todo
              projectId={props.projectId}
              todo={todo}
              todos={props.todos}
              todoDispatch={props.todoDispatch}
              selectedTodo={props.selectedTodo}
              setSelectedTodo={props.setSelectedTodo}
              labels={props.labels}
            />
          </li>
        ))}
      </ul>

      <h2 className="font-semibold">完了</h2>
      <ul className="flex flex-col gap-2">
        {completedTodos.map((todo) => (
          // Todo内容表示
          <li key={todo.id}>
            <Todo
              projectId={props.projectId}
              todo={todo}
              todos={props.todos}
              todoDispatch={props.todoDispatch}
              selectedTodo={props.selectedTodo}
              setSelectedTodo={props.setSelectedTodo}
              labels={props.labels}
            />
          </li>
        ))}
      </ul>
    </div>
  );
};

export default TodoList;
