import { Toaster } from "react-hot-toast";
import { Link, Outlet } from "react-router-dom";

const layout = () => {
  return (
    <div>
      <header>
        <nav>
          <Link to={"/"}>ホーム</Link>
          <Link to={"/signup"}>新規登録</Link>
          <Link to={"/login"}>ログイン</Link>
          <Link to={"/todos"}>アプリ</Link>
        </nav>
      </header>
      <main>
        <Toaster />
        <Outlet />
      </main>
    </div>
  );
};

export default layout;
