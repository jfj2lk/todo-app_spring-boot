import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { User } from "@/types/user";
import { apiRequest } from "@/utils/api";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

const Login = () => {
  const navigate = useNavigate();
  // 入力欄の値のstate
  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");

  // ログイン
  const handleLogin = async (email: string, password: string) => {
    const json = await apiRequest<User>("/api/auth/login", "POST", {
      email,
      password,
    });
    if (json.accessToken) {
      localStorage.setItem("accessToken", json.accessToken);
      navigate("/todos");
    }
  };

  return (
    <div className="flex flex-1 items-center justify-center bg-gray-100">
      <form
        onSubmit={(e) => {
          e.preventDefault();
          handleLogin(email, password);
        }}
        className="flex w-[25%] flex-col items-center gap-4 rounded-md bg-white px-5 py-10"
      >
        <h2 className="text-lg font-bold">ログイン</h2>

        {/* メールアドレス入力欄 */}
        <Input
          id="email"
          type="email"
          name="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          placeholder="メールアドレス"
        />

        {/* パスワード入力欄 */}
        <Input
          id="password"
          type="password"
          name="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="パスワード"
        />

        <Button>新規登録</Button>
      </form>
    </div>
  );
};

export default Login;
