import { User } from "@/types/user";
import { apiRequest } from "@/utils/api";
import { useState } from "react";

const Signup = () => {
  // 入力欄の値のstate
  const [name, setName] = useState<string>("");
  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");

  // 新規登録
  const handleSignup = async (
    name: string,
    email: string,
    password: string
  ) => {
    const json = await apiRequest<User>("/api/users", "POST", {
      user: { name, email, password },
    });
  };

  return (
    <div>
      <form
        onSubmit={(e) => {
          e.preventDefault();
          handleSignup(name, email, password);
        }}
      >
        <div
          style={{
            display: "grid",
            gridTemplateColumns: "100px 250px",
            gap: "5px",
          }}
        >
          {/* 名前入力欄 */}
          <label htmlFor="name">名前:</label>
          <input
            id="name"
            type="text"
            name="name"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />

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
        <button>新規登録</button>
      </form>
    </div>
  );
};

export default Signup;
