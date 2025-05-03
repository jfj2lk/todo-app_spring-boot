import { User } from "@/types/user";
import { apiRequest } from "@/utils/api";
import { useState } from "react";

const Login = () => {
  // 入力欄の値のstate
  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");

  // ログイン
  const handleLogin = async (email: string, password: string) => {
    const json = await apiRequest<User>("/api/login", "POST", {
      email,
      password,
    });
    if (json.accessToken) {
      localStorage.setItem("accessToken", json.accessToken);
    }
    console.log("ログイン成功！");
  };

  return (
    <div>
      <form
        onSubmit={(e) => {
          e.preventDefault();
          handleLogin(email, password);
        }}
      >
        <div
          style={{
            display: "grid",
            gridTemplateColumns: "100px 250px",
            gap: "5px",
          }}
        >
          {/* メールアドレス入力欄 */}
          <label htmlFor="email">メールアドレス:</label>
          <input
            id="email"
            type="email"
            name="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />

          {/* パスワード入力欄 */}
          <label htmlFor="password">パスワード:</label>
          <input
            id="password"
            type="password"
            name="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </div>
        <button>ログイン</button>
      </form>
    </div>
  );
};

export default Login;
