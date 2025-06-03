import { TodoType, TodoReducerActions } from "@/types/todo";

const todosReducer = (
  todos: TodoType[],
  action: TodoReducerActions,
): TodoType[] => {
  switch (action.type) {
    case "initialized": {
      return action.data;
    }

    case "added": {
      return [...todos, action.data];
    }

    case "updated": {
      return todos.map((todo) =>
        todo.id === action.data.id ? action.data : todo,
      );
    }

    case "deleted": {
      return todos.filter((todo) => todo.id !== action.id);
    }

    default: {
      throw Error("未定義のアクション");
    }
  }
};

export { todosReducer };
