import { useState, useEffect } from "react";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import NewAtendimentoModal from "./NovoAtendimento";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Badge } from "@/components/ui/badge";
import { Search } from "lucide-react";

const Atendimentos = () => {
  const [searchTerm, setSearchTerm] = useState("");
  const [atendimentos, setAtendimentos] = useState([]);

  const fetchAtendimentos = async () => {
    try {
      const token = localStorage.getItem("token");
      if (!token) {
        console.error("Nenhum token encontrado. Usuário não autenticado.");
        return;
      }

      const response = await fetch("http://localhost:8080/api/atendimentos/listar", {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      });

      if (!response.ok) {
        throw new Error("Erro ao buscar atendimentos");
      }

      const data = await response.json();

      const formatted = data.content.map((item: any) => {
        const dateObj = new Date(item.dataAtendimento);
        return {
          id: item.id,
          date: dateObj.toLocaleDateString("pt-BR"),
          time: dateObj.toLocaleTimeString("pt-BR", { hour: "2-digit", minute: "2-digit" }),
          patient: item.paciente?.nome ?? "—",
          procedure: item.procedimentos?.map((p: any) => p.nome).join(", "),
          type: item.tipoPagamento?.toLowerCase(),
          value: `R$ ${item.valorTotal.toFixed(2)}`,
          status: "Confirmado",
        };
      });

      setAtendimentos(formatted);
    } catch (error) {
      console.error("Erro ao buscar atendimentos:", error);
    }
  };

  useEffect(() => {
    fetchAtendimentos();
  }, []);

  const filteredAtendimentos = atendimentos.filter((a: any) =>
    a.patient.toLowerCase().includes(searchTerm.toLowerCase()) ||
    a.procedure.toLowerCase().includes(searchTerm.toLowerCase())
  );

  return (
    <div className="space-y-6 animate-fade-in">
      <div className="flex flex-col sm:flex-row justify-between items-start sm:items-center gap-4 animate-slide-in">
        <div>
          <h1 className="text-4xl font-bold tracking-tight font-display bg-clip-text text-transparent bg-gradient-to-r from-foreground to-foreground/70">
            Atendimentos
          </h1>
          <p className="text-muted-foreground mt-2 text-lg">
            Gerencie os atendimentos da clínica
          </p>
        </div>
        <NewAtendimentoModal onSuccess={() => fetchAtendimentos()} />
      </div>

      <Card className="glass-card border-border/50 animate-fade-in-up" style={{ animationDelay: "0.1s" }}>
        <CardHeader>
          <CardTitle>Lista de Atendimentos</CardTitle>
        </CardHeader>

        <CardContent>
          <div className="mb-4 relative">
            <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 w-4 h-4 text-muted-foreground" />
            <Input
              placeholder="Buscar por paciente"
              value={searchTerm}
              onChange={(e) => setSearchTerm(e.target.value)}
              className="pl-10"
            />
          </div>

          <div className="rounded-md border">
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Id</TableHead>
                  <TableHead>Data/Hora</TableHead>
                  <TableHead>Paciente</TableHead>
                  <TableHead>Procedimento</TableHead>
                  <TableHead>Tipo</TableHead>
                  <TableHead>Valor</TableHead>
                  <TableHead>Status</TableHead>
                </TableRow>
              </TableHeader>

              <TableBody>
                {filteredAtendimentos.map((appointment: any) => (
                  <TableRow key={appointment.id}>
                    <TableCell>{appointment.id}</TableCell>
                    <TableCell>
                      <div className="font-medium">{appointment.date}</div>
                      <div className="text-sm text-muted-foreground">{appointment.time}</div>
                    </TableCell>

                    <TableCell className="font-medium">{appointment.patient}</TableCell>

                    <TableCell>{appointment.procedure}</TableCell>

                    <TableCell>
                      <Badge variant={appointment.type === "plano" ? "secondary" : "outline"}>
                        {appointment.type === "plano" ? "Plano" : "Particular"}
                      </Badge>
                    </TableCell>

                    <TableCell className="font-medium">{appointment.value}</TableCell>

                    <TableCell>
                      <Badge variant="default">{appointment.status}</Badge>
                    </TableCell>
                  </TableRow>
                ))}

                {filteredAtendimentos.length === 0 && (
                  <TableRow>
                    <TableCell colSpan={6} className="text-center py-6 text-muted-foreground">
                      Nenhum atendimento encontrado.
                    </TableCell>
                  </TableRow>
                )}
              </TableBody>
            </Table>
          </div>
        </CardContent>
      </Card>
    </div>
  );
};

export default Atendimentos;
