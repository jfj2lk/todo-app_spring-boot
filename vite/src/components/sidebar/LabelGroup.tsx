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
import { useAppSelector } from "@/store";
import {
  createLabel,
  deleteLabel,
  getAllLabels,
  labelSelectors,
  updateLabel,
} from "@/store/labelStore";
import { defaultLabelFormValues, labelFormSchema } from "@/types/label";
import { Tag } from "lucide-react";
import { CreateEntityButton } from "../entity/button/CreateEntityButton";
import { DeleteEntityButton } from "../entity/button/DeleteEntityButton";
import { UpdateEntityButton } from "../entity/button/UpdateEntityButton";
import { EntityManagerProvider } from "../entity/EntityManagerProvider";
import { CollapsibleTriggerButton } from "./CollapsibleTriggerButton";

const LabelGroup = () => {
  const labels = useAppSelector(labelSelectors.selectAll);

  return (
    <EntityManagerProvider
      entities={labels}
      getAllEntities={getAllLabels}
      createEntity={createLabel}
      updateEntity={updateLabel}
      deleteEntity={deleteLabel}
      formSchema={labelFormSchema}
      defaultFormValues={defaultLabelFormValues}
      entityName="ラベル"
    >
      <Collapsible defaultOpen className="group/collapsible">
        <SidebarGroup className="text-gray-500">
          {/* ラベル */}
          <SidebarGroupLabel className="text-gray-500">
            ラベル
          </SidebarGroupLabel>

          {/* アクション */}
          <SidebarGroupAction className="justify-end">
            {/* エンティティ作成ボタン */}
            <CreateEntityButton />

            {/* コラプシブル開閉ボタン */}
            <CollapsibleTrigger>
              <CollapsibleTriggerButton />
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
                      <UpdateEntityButton entity={label} />
                      <DeleteEntityButton entity={label} />
                    </SidebarMenuAction>
                  </SidebarMenuItem>
                ))}
              </SidebarMenu>
            </SidebarGroupContent>
          </CollapsibleContent>
        </SidebarGroup>
      </Collapsible>
    </EntityManagerProvider>
  );
};

export { LabelGroup };
