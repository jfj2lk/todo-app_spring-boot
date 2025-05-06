import { ApiResponse, HttpMethod, RequestBody } from "@/types/api";

const apiRequest = async <T>(
  url: string,
  method: HttpMethod = "GET",
  body: RequestBody = null
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

    // エラーレスポンスの場合は例外を投げる
    if (!res.ok) {
      // エラーメッセージを整形
      const errorMessages = json.errors?.map((error) => {
        return "\n・" + error.defaultMessage;
      });
      throw Error(
        [`${json.message}`, `Validation Error: ${errorMessages}`].join("\n\n")
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
      ].join("\n\n")
    );
  }
};

export { apiRequest };
