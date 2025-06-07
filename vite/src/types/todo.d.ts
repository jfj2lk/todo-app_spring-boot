// TodoとLabelの中間テーブルのレコード
export type TodoLabel = {
  labelId: number;
};

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
  todoLabels: TodoLabel[];
};

// リデューサのアクションタイプ
type TodoReducerActionTypes = "initialized" | "added" | "updated" | "deleted";

// リデューサのアクション
export type TodoReducerActions = { type: TodoReducerActionTypes } & Record<
  string,
  any
>;
