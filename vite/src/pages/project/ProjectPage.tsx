import { ProjectType } from "@/types/project";
import axios from "axios";
import { useEffect, useState } from "react";
import CreateProjectForm from "./CreateProjectForm";
import ProjectList from "./ProjectList";

const ProjectPage = () => {
  const [projects, setProjects] = useState<ProjectType[]>([]);

  useEffect(() => {
    // 全てのProjectを取得する
    const fetchAllProjects = () => {
      axios.get("/api/projects").then((response) => {
        setProjects(response.data.data);
      });
    };
    fetchAllProjects();
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
