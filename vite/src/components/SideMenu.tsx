import { Link } from "react-router-dom";

const items = [
  { label: "ユーザー名", link: "#" },
  { label: "ラベル管理", link: "#" },
  { label: "リスト管理", link: "#" },
];

const SideMenu = () => {
  return (
    <aside className="w-[10%] bg-blue-400 px-4 py-5 text-lg font-bold text-white">
      <nav>
        <ul className="flex flex-col gap-5">
          {items.map((item) => (
            <li
              key={item.link}
              className="rounded-md p-1 pl-2 hover:bg-blue-500"
            >
              <Link to={item.link}>{item.label}</Link>
            </li>
          ))}
        </ul>
      </nav>
    </aside>
  );
};

export default SideMenu;
