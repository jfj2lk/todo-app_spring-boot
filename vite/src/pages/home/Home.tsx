import { Link } from "react-router-dom";
import { CheckCircle, ListTodo, Star } from "lucide-react";
import { Button } from "@/components/ui/button";

const Home = () => {
  return (
    <div>
      {/* ヒーローセクション */}
      <section className="flex flex-col items-center gap-8 bg-amber-200 py-10 text-center">
        <h1 className="text-3xl leading-tight font-extrabold md:text-4xl lg:text-5xl">
          シンプルで使いやすい
          <br />
          Todoアプリケーション
        </h1>

        <p className="text-lg text-gray-500 md:text-xl">
          MyTodoで日々のタスク管理をもっと簡単に。いつでもどこでも、あなたのタスクを整理しましょう。
        </p>

        <div className="flex gap-4">
          <Link to="/signup">
            <Button size="lg">無料で始める</Button>
          </Link>
          <Link to="/login">
            <Button variant="outline" size="lg">
              ログイン
            </Button>
          </Link>
        </div>

        <div>
          <img src="" alt="アプリ画面のスクリーンショット" />
        </div>
      </section>

      {/* 特徴セクション */}
      <section className="py-20">
        <h2 className="mb-8 text-center text-2xl font-bold md:text-3xl">
          主な機能
        </h2>
        <div className="grid gap-8 md:grid-cols-3">
          <div className="flex flex-col items-center text-center">
            <div className="bg-primary/10 mb-4 rounded-full p-4">
              <ListTodo className="text-primary h-8 w-8" />
            </div>
            <h3 className="mb-2 text-xl font-bold">シンプルなタスク管理</h3>
            <p className="text-muted-foreground">
              直感的なインターフェースで、タスクの追加、編集、完了が簡単にできます。
            </p>
          </div>
          <div className="flex flex-col items-center text-center">
            <div className="bg-primary/10 mb-4 rounded-full p-4">
              <CheckCircle className="text-primary h-8 w-8" />
            </div>
            <h3 className="mb-2 text-xl font-bold">タスク完了の追跡</h3>
            <p className="text-muted-foreground">
              完了したタスクを一目で確認でき、達成感を得られます。
            </p>
          </div>
          <div className="flex flex-col items-center text-center">
            <div className="bg-primary/10 mb-4 rounded-full p-4">
              <Star className="text-primary h-8 w-8" />
            </div>
            <h3 className="mb-2 text-xl font-bold">重要タスクの優先付け</h3>
            <p className="text-muted-foreground">
              重要なタスクに優先順位をつけて、効率的に作業を進めることができます。
            </p>
          </div>
        </div>
      </section>

      {/* CTAセクション */}
      <section className="bg-slate-100 pt-12 pb-9">
        <div className="flex flex-col items-center gap-5 text-center">
          <h2 className="text-3xl font-bold">今すぐ始めましょう</h2>
          <p className="mb-3 text-slate-400">
            MyTodoは完全無料で利用できます。今すぐアカウントを作成して、タスク管理を始めましょう。
          </p>
          <div className="flex gap-4">
            <Link to="/register">
              <Button size="lg">無料で登録</Button>
            </Link>
            <Link to="/login">
              <Button variant="outline" size="lg">
                ログイン
              </Button>
            </Link>
          </div>
        </div>
      </section>
    </div>
  );
};

export default Home;
