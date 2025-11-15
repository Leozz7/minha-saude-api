import { useEffect, useState } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Users, Calendar, FileText, Clock, CheckCircle } from "lucide-react";

const Dashboard = () => {
  const [totalPacientes, setTotalPacientes] = useState(0);
  const [totalProcedimentos, setTotalProcedimentos] = useState(0);
  const [totalAtendimentosHoje, setTotalAtendimentosHoje] = useState(0);

  const [recentes, setRecentes] = useState<any[]>([]);
  const [proximos, setProximos] = useState<any[]>([]);

  const loadData = async () => {
    try {
      const token = localStorage.getItem("token");

      const headers = {
        "Content-Type": "application/json",
        Authorization: `Bearer ${token}`,
      };

      // ---- Pacientes ----
      const pacientesRes = await fetch("http://localhost:8080/api/pacientes/listar", { headers });
      const pacientesData = await pacientesRes.json();
      setTotalPacientes(pacientesData.page.totalElements);

      // ---- Procedimentos ----
      const procRes = await fetch("http://localhost:8080/api/procedimentos/listar", { headers });
      const procData = await procRes.json();
      setTotalProcedimentos(procData.page.totalElements);

      // ---- Atendimentos ----
      const atendRes = await fetch("http://localhost:8080/api/atendimentos/listar", { headers });
      const atendData = await atendRes.json();

      const lista = atendData; // vem SEM page/content → já é um array

      const hoje = new Date().toISOString().split("T")[0];

      // Atendimentos de hoje
      const atendHoje = lista.filter((a: any) =>
        a.dataAtendimento?.startsWith(hoje)
      );
      setTotalAtendimentosHoje(atendHoje.length);

      // Atendimentos mais recentes (últimos 4)
      const sorted = [...lista].sort(
        (a, b) => new Date(b.dataAtendimento).getTime() - new Date(a.dataAtendimento).getTime()
      );
      setRecentes(sorted.slice(0, 4));

      // Próximos agendamentos (data futura)
      const futuros = lista.filter((a: any) =>
        new Date(a.dataAtendimento).getTime() > Date.now()
      );
      const futurosOrdenados = futuros.sort(
        (a, b) => new Date(a.dataAtendimento).getTime() - new Date(b.dataAtendimento).getTime()
      );
      setProximos(futurosOrdenados.slice(0, 4));

    } catch (error) {
      console.error("Erro ao carregar dados do dashboard:", error);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const stats = [
    {
      title: "Total de Pacientes",
      value: totalPacientes,
      icon: Users,
      color: "text-primary",
      gradient: "from-primary/20 to-primary/5",
    },
    {
      title: "Procedimentos Cadastrados",
      value: totalProcedimentos,
      icon: FileText,
      color: "text-accent",
      gradient: "from-accent/20 to-accent/5",
    },
    {
      title: "Atendimentos Hoje",
      value: totalAtendimentosHoje,
      icon: Calendar,
      color: "text-secondary",
      gradient: "from-secondary/20 to-secondary/5",
    },
  ];

  // função util para formatar data
  const formatarData = (dateString: string) => {
    const d = new Date(dateString);
    return d.toLocaleDateString("pt-BR");
  };

  const formatarHora = (dateString: string) => {
    const d = new Date(dateString);
    return d.toLocaleTimeString("pt-BR", { hour: "2-digit", minute: "2-digit" });
  };

  return (
    <div className="space-y-8 animate-fade-in">
      <div className="animate-slide-in">
        <h1 className="text-4xl font-bold tracking-tight font-display bg-clip-text text-transparent bg-gradient-to-r from-foreground to-foreground/70">
          Dashboard
        </h1>
        <p className="text-muted-foreground mt-2 text-lg">
          Visão geral dos dados da clínica
        </p>
      </div>

      {/* === CARDS === */}
      <div className="grid gap-6 md:grid-cols-2 lg:grid-cols-3">
        {stats.map((stat, index) => (
          <Card
            key={stat.title}
            className={`hover-lift glass-card border-border/50 group animate-fade-in-up bg-gradient-to-br ${stat.gradient}`}
            style={{ animationDelay: `${index * 0.1}s` }}
          >
            <CardHeader className="flex flex-row items-center justify-between pb-3">
              <CardTitle className="text-sm font-medium text-muted-foreground">
                {stat.title}
              </CardTitle>
              <div className="p-3 rounded-xl bg-white/10 group-hover:scale-110 transition-transform">
                <stat.icon className={`w-6 h-6 ${stat.color}`} />
              </div>
            </CardHeader>

            <CardContent>
              <div className="text-4xl font-bold font-display">
                {stat.value}
              </div>
            </CardContent>
          </Card>
        ))}
      </div>

      {/* === LISTAS === */}
      <div className="grid gap-6 md:grid-cols-2">
        {/* ATENDIMENTOS RECENTES */}
        <Card className="glass-card border-border/50 hover-lift animate-fade-in-up">
          <CardHeader className="border-b border-border/50">
            <CardTitle className="flex items-center gap-2 text-xl font-display">
              <Clock className="w-5 h-5 text-primary" />
              Atendimentos Recentes
            </CardTitle>
          </CardHeader>
          <CardContent className="pt-6 space-y-3">
            {recentes.map((a: any, index) => (
              <div
                key={a.id}
                className="flex items-center justify-between p-4 bg-gradient-to-r from-muted/50 to-transparent rounded-xl border border-border/30 hover:border-primary/30 transition-all duration-300 hover:shadow-md"
                style={{ animationDelay: `${0.3 + index * 0.1}s` }}
              >
                <div className="flex-1">
                  <p className="font-semibold">{a.paciente.nome}</p>
                  <p className="text-sm text-muted-foreground">{a.procedimentos[0]?.nome}</p>
                </div>
                <div className="text-right space-y-1">
                  <p className="font-bold">R$ {a.valorTotal.toFixed(2)}</p>
                  <p className="text-xs text-muted-foreground">
                    {formatarData(a.dataAtendimento)} - {formatarHora(a.dataAtendimento)}
                  </p>
                </div>
              </div>
            ))}
          </CardContent>
        </Card>

        {/* PRÓXIMOS AGENDAMENTOS */}
        <Card className="glass-card border-border/50 hover-lift animate-fade-in-up">
          <CardHeader className="border-b border-border/50">
            <CardTitle className="flex items-center gap-2 text-xl font-display">
              <CheckCircle className="w-5 h-5 text-secondary" />
              Próximos Agendamentos
            </CardTitle>
          </CardHeader>
          <CardContent className="pt-6 space-y-3">
            {proximos.map((a: any, index) => (
              <div
                key={a.id}
                className="flex items-center justify-between p-4 bg-gradient-to-r from-muted/50 to-transparent rounded-xl border border-border/30 hover:border-secondary/30 transition-all duration-300 hover:shadow-md"
                style={{ animationDelay: `${0.3 + index * 0.1}s` }}
              >
                <div className="flex-1">
                  <p className="font-semibold">{a.paciente.nome}</p>
                  <p className="text-sm text-muted-foreground">{a.procedimentos[0]?.nome}</p>
                </div>
                <div className="text-right">
                  <p className="font-bold">{formatarData(a.dataAtendimento)}</p>
                  <p className="text-xs text-muted-foreground mt-1">
                    {formatarHora(a.dataAtendimento)}
                  </p>
                </div>
              </div>
            ))}
          </CardContent>
        </Card>
      </div>
    </div>
  );
};

export default Dashboard;
