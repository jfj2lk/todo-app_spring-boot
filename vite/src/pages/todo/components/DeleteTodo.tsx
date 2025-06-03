import { TodoType, TodoReducerActions } from "@/types/todo";
import { apiRequest } from "@/utils/api";

const DeleteTodo = (props: {
  todo: TodoType;
  todos: TodoType[];
  todoDispatch: React.Dispatch<TodoReducerActions>;
}) => {
  // Todo削除
  const handleDeleteTodo = async (deleteTodoId: number) => {
    const json = await apiRequest<TodoType>(
      `/api/todos/${deleteTodoId}`,
      "DELETE",
    );
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
