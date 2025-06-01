import { ApiResponse, HttpMethod, RequestBody } from "@/types/api";
import toast from "react-hot-toast";

const apiRequest = async <T>(
  url: string,
  method: HttpMethod = "GET",
  body: RequestBody = null,
): Promise<ApiResponse<T>> => {
  try {
    // ローカルストレージの値取得
    const accessToken = localStorage.getItem("accessToken");
    // APIリクエストオプションの設定
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

    // APIリクエスト & レスポンス取得
    const res = await fetch(url, options);
    const json: ApiResponse<T> = await res.json();

    // レスポンスメッセージが存在する場合は、その種類に応じてトースト表示する
    if (json.message) {
      if (res.ok) {
        toast.success(json.message);
      } else {
        toast.error(json.message);

        // 401エラーの場合はホーム画面に遷移させる
        if (res.status === 401) {
          location.href = "/";
        }
      }
    }

    // バリデーションエラーメッセージを整形する
    if (!res.ok) {
      const errorMessages = json.errors?.map((error) => {
        return "\n・" + error.defaultMessage;
      });
      throw Error(
        [`${json.message}`, `Validation Error: ${errorMessages}`].join("\n\n"),
      );
    }

    // レスポンスのJSONを呼び出し元へ返す
    return json;
  } catch (error) {
    // 例外メッセージの設定
    throw Error(
      [
        "",
        "APIリクエストでエラーが発生しました。",
        `URL:${url}`,
        `${error}`,
      ].join("\n\n"),
    );
  }
};

export { apiRequest };
