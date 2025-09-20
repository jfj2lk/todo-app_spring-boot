import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarHeader,
  SidebarMenu,
  SidebarMenuItem,
  SidebarTrigger,
} from "@/components/ui/sidebar";
import { LabelGroup } from "@/pages/label/LabelGroup";
import { ProjectGroup } from "@/pages/project/ProjectGroup";
import { AccountInfoMenu } from "@/pages/sidebar/AccountInfoMenu";

const AppSidebar = () => {
  return (
    <Sidebar variant="floating" collapsible="icon">
      {/* ヘッダー */}
      <SidebarHeader>
        <SidebarMenu>
          <SidebarMenuItem>
            <SidebarTrigger />
          </SidebarMenuItem>
          <SidebarMenuItem>
            {/* アカウント欄 */}
            <AccountInfoMenu />
          </SidebarMenuItem>
        </SidebarMenu>
      </SidebarHeader>

      {/* コンテンツ */}
      <SidebarContent>
        {/* ラベル */}
        <LabelGroup />
        {/* プロジェクト */}
        <ProjectGroup />
      </SidebarContent>

      {/* フッター */}
      <SidebarFooter />
    </Sidebar>
  );
};
export default AppSidebar;
