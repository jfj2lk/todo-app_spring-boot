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
    <div className="mx-auto max-w-4xl p-6">
      <div className="flex items-center justify-between">
        <h1 className="text-2xl font-semibold">プロジェクト</h1>
        {/* プロジェクト作成フォーム */}
        <CreateProjectForm projects={projects} setProjects={setProjects} />
      </div>

      {/* プロジェクト一覧 */}
      <ProjectList projects={projects} setProjects={setProjects} />
    </div>
  );
};

export default ProjectPage;
