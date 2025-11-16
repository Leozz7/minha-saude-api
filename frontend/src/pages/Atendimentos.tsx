import { useState, useEffect } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
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
  const [atendimento, setatendimento] = useState([]);

  useEffect(() => {
    const fetchatendimento = async () => {
      try {
        const token = localStorage.getItem("token");

        const response = await fetch("http://localhost:8080/api/atendimentos/listar", {
          headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`,
          },
        });

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

        setatendimento(formatted);
      } catch (error) {
        console.error("Erro ao buscar atendimentos:", error);
      }
    };

    fetchatendimento();
  }, []);

  const filteredatendimento = atendimento.filter((a: any) => {
    const term = searchTerm.toLowerCase();

    return (
      a.patient.toLowerCase().includes(term) ||
      a.procedure.toLowerCase().includes(term)
    );
  });

  return (
    <div className="space-y-6 animate-fade-in">

      <Card className="glass-card border-border/50 animate-fade-in-up" style={{ animationDelay: "0.2s" }}>
        <CardHeader className="border-b border-border/50">
          <CardTitle className="text-xl font-display">Lista de Atendimentos</CardTitle>
        </CardHeader>

        <CardContent>
          <div className="mb-4">
            <div className="relative">
              <Search className="absolute left-3 top-1/2 transform -translate-y-1/2 w-4 h-4 text-muted-foreground" />
              <Input
                placeholder="Buscar por paciente ou procedimento..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="pl-10"
              />
            </div>
          </div>

          <div className="rounded-md border">
            <Table>
              <TableHeader>
                <TableRow>
                  <TableHead>Data/Hora</TableHead>
                  <TableHead>Paciente</TableHead>
                  <TableHead>Procedimento</TableHead>
                  <TableHead>Tipo</TableHead>
                  <TableHead>Valor</TableHead>
                  <TableHead>Status</TableHead>
                </TableRow>
              </TableHeader>

              <TableBody>
                {filteredatendimento.map((appointment: any) => (
                  <TableRow key={appointment.id}>
                    <TableCell>
                      <div className="font-medium">{appointment.date}</div>
                      <div className="text-sm text-muted-foreground">
                        {appointment.time}
                      </div>
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
                      <Badge variant={appointment.status === "Confirmado" ? "default" : "outline"}>
                        {appointment.status}
                      </Badge>
                    </TableCell>
                  </TableRow>
                ))}

                {filteredatendimento.length === 0 && (
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
