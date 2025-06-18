import { ProjectType } from "@/types/project";
import { apiRequest } from "@/utils/api";
import { useEffect, useState } from "react";
import CreateProjectForm from "./CreateProjectForm";
import ProjectList from "./ProjectList";

const ProjectPage = () => {
  const [projects, setProjects] = useState<ProjectType[]>([]);

  useEffect(() => {
    (async () => {
      const json = await apiRequest<ProjectType[]>("/api/projects");
      setProjects(json.data);
    })();
  }, []);

  return (
    <div>
      <h2>プロジェクト</h2>
      {/* プロジェクト作成フォーム */}
      <CreateProjectForm projects={projects} setProjects={setProjects} />

      {/* プロジェクト一覧 */}
      <ProjectList projects={projects} setProjects={setProjects} />
    </div>
  );
};

export default ProjectPage;
