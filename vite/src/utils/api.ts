import { ApiResponse, HttpMethod, RequestBody } from "@/types/api";
import toast from "react-hot-toast";

const apiRequest = async <T>(
  url: string,
  method: HttpMethod = "GET",
  body: RequestBody = null,
): Promise<ApiResponse<T>> => {
  try {
    // アクセストークン取得
    const accessToken = localStorage.getItem("accessToken");

    // リクエストオプションの作成
    const options: RequestInit = {
      headers: {
        Accept: "application/json",
        ...(accessToken && { Authorization: `Bearer ${accessToken}` }),
        ...(body && {
          "Content-Type": "application/json",
        }),
      },
      method,
      ...(body && {
        body: JSON.stringify(body),
      }),
    };

    // リクエスト実行
    const res = await fetch(url, options);
    const json: ApiResponse<T> = await res.json();

    // レスポンスメッセージ表示
    if (json.message) {
      if (res.ok) {
        toast.success(json.message);
      } else {
        toast.error(json.message);
      }
    }

    // エラーハンドリング
    if (!res.ok) {
      // 401 の場合はリダイレクト
      if (res.status === 401) {
        localStorage.removeItem("accessToken");
        localStorage.removeItem("userInfo");
        location.href = "/";
      }

      throw Error("APIリクエストでエラーが発生しました。");
    }

    // レスポンスのJSONを呼び出し元へ返す
    return json;
  } catch (e) {
    throw Error(String(e));
  }
};

export { apiRequest };
