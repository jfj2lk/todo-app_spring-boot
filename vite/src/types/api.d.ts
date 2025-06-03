// HTTPメソッド
export type HttpMethod = "GET" | "POST" | "PATCH" | "DELETE";

// リクエストボディ
export type RequestBody = object | null;

// レスポンスのJSONオブジェクト
export type ApiResponse<T> = {
  data: T extends void ? never : T;
  message?: string;
  accessToken?: string;
  errors?: [{ defaultMessage?: string }];
  userInfo?: { id: number; name: string; email: string };
};
