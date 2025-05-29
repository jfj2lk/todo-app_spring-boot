import { Link } from "react-router-dom";
import logo from "@/assets/logo.svg";

// ヘッダーメニューの要素
const items: { label: string; link: string }[] = [
  { label: "ログイン", link: "/login" },
  { label: "新規登録", link: "/signup" },
];

const Header = () => {
  return (
    <header className="flex items-center justify-between px-6 py-4 shadow">
      {/* ヘッダーロゴ */}
      <div>
        <Link to={"/"}>
          <img src={logo} className="h-10 w-10" alt="ロゴ画像" />
        </Link>
      </div>

      {/* ヘッダーメニュー */}
      <div>
        <nav className="flex gap-5">
          {items.map((item) => (
            <Link
              key={item.link}
              to={item.link}
              className={`rounded-md p-2 font-bold ${item.link === "/signup" ? "bg-blue-400 text-neutral-50" : ""}`}
            >
              {item.label}
            </Link>
          ))}
        </nav>
      </div>
    </header>
  );
};

export default Header;
