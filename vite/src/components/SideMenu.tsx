import { Link } from "react-router-dom";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "./ui/dropdown-menu";

const menus = [
  { label: "ラベル管理", link: "#" },
  { label: "リスト管理", link: "#" },
];

const SideMenu = () => {
  const userInfoString: string | null = localStorage.getItem("userInfo");
  const userInfo: any | null = userInfoString
    ? JSON.parse(userInfoString)
    : null;

  const handleLogout = async () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("userInfo");
    location.href = "/";
  };

  return (
    <aside className="w-[10%] bg-blue-400 px-4 py-5 text-lg font-bold text-white">
      <nav className="flex h-full flex-col justify-between">
        <ul className="flex flex-1 flex-col gap-5">
          <li className="rounded-md p-1 pl-2 hover:bg-blue-500">
            <DropdownMenu>
              <DropdownMenuTrigger className="w-full rounded-md text-left">
                {userInfo?.name}
              </DropdownMenuTrigger>
              <DropdownMenuContent>
                <DropdownMenuLabel>
                  <p>{userInfo?.name}</p>
                  <p className="text-sm text-gray-400">{userInfo?.email}</p>
                </DropdownMenuLabel>
                <DropdownMenuSeparator />
                <DropdownMenuItem>
                  <form
                    onSubmit={(e) => {
                      e.preventDefault();
                      handleLogout();
                    }}
                  >
                    <button type="submit">ログアウト</button>
                  </form>
                </DropdownMenuItem>
              </DropdownMenuContent>
            </DropdownMenu>
          </li>
          {menus.map((menu) => (
            <li
              key={menu.link}
              className="rounded-md p-1 pl-2 hover:bg-blue-500"
            >
              <Link to={menu.link}>{menu.label}</Link>
            </li>
          ))}
        </ul>
      </nav>
    </aside>
  );
};
export default SideMenu;
