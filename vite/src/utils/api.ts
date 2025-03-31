import { ApiResponse, HttpMethod, RequestBody } from "@/types/api";

const apiRequest = async <T>(
  url: string,
  method: HttpMethod = "GET",
  body: RequestBody = null
): Promise<ApiResponse<T>> => {
  try {
    // APIリクエストオプションの設定
    const options: RequestInit = {
      headers: Object.assign(
        {
          Accept: "application/json",
        },
        body && {
          "Content-Type": "application/json",
        }
      ),
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
      // バリデーションエラーメッセージを整形
      const validationErrorMessages = json.validationErrorMessages?.map(
        (message) => {
          return "・" + message.defaultMessage;
        }
      );
      throw Error(`\n${validationErrorMessages}`);
    }

    // レスポンスのJSONを呼び出し元へ返す
    return json;
  } catch (error) {
    // 例外メッセージの設定
    throw Error(
      [
        "\n",
        "APIリクエストでエラーが発生しました。",
        `URL:${url}`,
        `${error}`,
      ].join("\n")
    );
  }
};

export { apiRequest };
