// HTTPメソッド
export type HttpMethod = "GET" | "POST" | "PATCH" | "DELETE";

// リクエストボディ
export type RequestBody = object | null;

// レスポンスのJSONオブジェクト
export type ApiResponse<T> = {
  data?: T;
  message?: string;
  accessToken?: string;
  errors?: [{ defaultMessage?: string }];
};
