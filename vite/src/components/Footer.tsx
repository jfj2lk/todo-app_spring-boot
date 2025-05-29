import { Link } from "react-router-dom";

// フッターメニューの要素
const items: { label: string; link: string }[] = [
  { label: "プライバシー", link: "#" },
  { label: "利用規約", link: "#" },
  { label: "お問い合わせ", link: "#" },
];

const Footer = () => {
  return (
    <footer className="border-t px-6 py-6 text-sm text-gray-500">
      <div className="flex flex-col items-center justify-between gap-2 sm:flex-row">
        <p>&copy; 2025 MyTodo. All rights reserved.</p>
        <div className="flex gap-4">
          {items.map((item) => (
            <Link key={item.link} to={item.link} className="hover:underline">
              {item.label}
            </Link>
          ))}
        </div>
      </div>
    </footer>
  );
};

export default Footer;
