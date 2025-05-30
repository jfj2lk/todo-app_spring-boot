import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { User } from "@/types/user";
import { apiRequest } from "@/utils/api";
import { useState } from "react";
import { useNavigate } from "react-router-dom";

const Signup = () => {
  const navigate = useNavigate();
  // 入力欄の値のstate
  const [name, setName] = useState<string>("");
  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");

  // 新規登録
  const handleSignup = async (
    name: string,
    email: string,
    password: string,
  ) => {
    const json = await apiRequest<User>("/api/auth/signup", "POST", {
      name,
      email,
      password,
    });
    if (json.accessToken) {
      localStorage.setItem("accessToken", json.accessToken);
      navigate("/todos");
    }
  };

  return (
    <div className="flex h-lvh items-center justify-center bg-gray-100">
      <form
        onSubmit={(e) => {
          e.preventDefault();
          handleSignup(name, email, password);
        }}
        className="flex w-[25%] flex-col items-center gap-4 rounded-md bg-white px-5 py-10"
      >
        <h2 className="text-lg font-bold">新規登録</h2>
        {/* 名前入力欄 */}
        <Input
          id="name"
          type="text"
          name="name"
          value={name}
          onChange={(e) => setName(e.target.value)}
          placeholder="名前"
        />

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

export default Signup;
