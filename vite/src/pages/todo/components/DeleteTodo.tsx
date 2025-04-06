import { Todo, TodoReducerActions } from "@/types/todo";
import { apiRequest } from "@/utils/api";

const DeleteTodo = (props: {
  todo: Todo;
  todos: Todo[];
  todoDispatch: React.Dispatch<TodoReducerActions>;
}) => {
  // Todo削除
  const handleDeleteTodo = async (deleteTodoId: number) => {
    const json = await apiRequest<Todo>(`/api/todos/${deleteTodoId}`, "DELETE");
    props.todoDispatch({ type: "deleted", id: json.data });
  };

  return (
    <div>
      <form
        onSubmit={(e) => {
          e.preventDefault();
          handleDeleteTodo(props.todo.id);
        }}
      >
        <button>delete</button>
      </form>
    </div>
  );
};

export default DeleteTodo;
