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

const ProjectGroup = () => {
  const projects = useAppSelector(projectSelectors.selectAll);

  return (
    <BaseSidebarGroup
      entities={projects}
      getAllEntities={getAllProjects}
      createEntity={createProject}
      updateEntity={updateProject}
      deleteEntity={deleteProject}
      formSchema={projectFormSchema}
      defaultFormValues={defaultProjectFormValues}
      entityName="プロジェクト"
    />
  );
};

export { ProjectGroup };
