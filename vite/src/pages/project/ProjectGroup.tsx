import { BaseSidebarGroup } from "@/components/common/BaseSidebarGroup";
import { useAppSelector } from "@/store";
import {
  createProject,
  deleteProject,
  getAllProjects,
  projectSelectors,
  updateProject,
} from "@/store/project-store";
import { defaultProjectFormValues, projectFormSchema } from "@/types/project";
import { Folder } from "lucide-react";

const ProjectGroup = () => {
  const projects = useAppSelector(projectSelectors.selectAll);

  return (
    <BaseSidebarGroup
      entity={projects}
      getEntity={getAllProjects}
      createEntity={createProject}
      updateEntity={updateProject}
      deleteEntity={deleteProject}
      formSchema={projectFormSchema}
      defaultFormValues={defaultProjectFormValues}
      labelName="プロジェクト"
      resourcePath="/todos?projectId=:id"
      entityIcon={<Folder size={16} />}
    />
  );
};

export { ProjectGroup };
