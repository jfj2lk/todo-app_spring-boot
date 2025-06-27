import { Button } from "@/components/ui/button";
import {
  Collapsible,
  CollapsibleContent,
  CollapsibleTrigger,
} from "@/components/ui/collapsible";
import {
  SidebarGroup,
  SidebarGroupAction,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarMenu,
  SidebarMenuAction,
  SidebarMenuButton,
  SidebarMenuItem,
} from "@/components/ui/sidebar";
import { useAppDispatch, useAppSelector } from "@/store";
import { getAllLabels, labelSelectors } from "@/store/labelStore";
import { ChevronDown, Pencil, Plus, Tag, Trash2 } from "lucide-react";
import { ReactNode, useEffect } from "react";

const LabelGroup = () => {
  const labels = useAppSelector(labelSelectors.selectAll);
  const dispatch = useAppDispatch();

  useEffect(() => {
    const handleGetAllEntities = () => {
      dispatch(getAllLabels());
    };
    handleGetAllEntities();
  }, []);

  const SidebarIcon = (props: { children: ReactNode }) => {
    return (
      <Button variant={"ghost"} size={"icon"} className="text-gray-500">
        {props.children}
      </Button>
    );
  };

  return (
    <Collapsible>
      <SidebarGroup className="text-gray-500">
        {/* ラベル */}
        <SidebarGroupLabel className="text-gray-500">ラベル</SidebarGroupLabel>

        {/* アクション */}
        <SidebarGroupAction className="justify-end">
          <SidebarIcon>
            <Plus />
          </SidebarIcon>

          <CollapsibleTrigger>
            <SidebarIcon>
              <ChevronDown />
            </SidebarIcon>
          </CollapsibleTrigger>
        </SidebarGroupAction>

        {/* コンテンツ */}
        <CollapsibleContent>
          <SidebarGroupContent>
            <SidebarMenu>
              {/* アイテム */}
              {labels.map((label) => (
                <SidebarMenuItem key={label.id}>
                  {/* ボタン */}
                  <SidebarMenuButton asChild>
                    <a href={"#"}>
                      <Tag />
                      <span className="w-[57.5%] truncate">{label.name}</span>
                    </a>
                  </SidebarMenuButton>

                  {/* アクションボタン */}
                  <SidebarMenuAction showOnHover className="justify-end">
                    <SidebarIcon>
                      <Pencil />
                    </SidebarIcon>
                    <SidebarIcon>
                      <Trash2 />
                    </SidebarIcon>
                  </SidebarMenuAction>
                </SidebarMenuItem>
              ))}
            </SidebarMenu>
          </SidebarGroupContent>
        </CollapsibleContent>
      </SidebarGroup>
    </Collapsible>
  );
};

export { LabelGroup };
