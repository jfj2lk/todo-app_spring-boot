// Todoモデル
export type TodoType = {
  id: number;
  isCompleted: boolean;
  name: string;
  desc: string;
  priority: number;
  dueDate: string;
  dueTime: string;
  createdAt: any;
  updatedAt: any;
};

// リデューサのアクションタイプ
type TodoReducerActionTypes = "initialized" | "added" | "updated" | "deleted";

// リデューサのアクション
export type TodoReducerActions = { type: TodoReducerActionTypes } & Record<
  string,
  any
>;
