import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Card, CardContent, CardDescription, CardHeader, CardTitle } from "@/components/ui/card";
import { useToast } from "@/hooks/use-toast";
import { Activity, Lock, User, Sparkles } from "lucide-react";

const Login = () => {
  const [nome, setNome] = useState("");
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [isLoading, setIsLoading] = useState(false);
  const [isRegister, setIsRegister] = useState(false);

  const navigate = useNavigate();
  const { toast } = useToast();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setIsLoading(true);

    try {
      const url = isRegister
        ? "http://localhost:8080/api/usuarios/criar"
        : "http://localhost:8080/api/usuarios/login";

      const body = isRegister
        ? { nome, email, senha }
        : { email, senha };

      const response = await fetch(url, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(body),
      });

      if (!response.ok) {
        throw new Error("Erro na requisição");
      }

      const data = await response.json();

      if (isRegister) {
        toast({
          title: "Conta criada!",
          description: "Você já pode acessar sua conta.",
        });

        navigate("/dashboard");

        setIsRegister(false);
        setSenha("");
        return;
      }

      if (!data.token) {
        throw new Error("Token não recebido do servidor");
      }

      localStorage.setItem("token", data.token);
      localStorage.setItem("nome", data.nome);
      localStorage.setItem("email", data.email);

      toast({
        title: "Login bem-sucedido",
        description: `Bem-vindo de volta, ${data.nome}!`,
      });

      navigate("/dashboard");

    } catch (error) {
      toast({
        title: isRegister ? "Erro ao registrar" : "Erro no login",
        description: "Verifique os dados e tente novamente",
        variant: "destructive",
      });
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center p-4 relative overflow-hidden bg-gradient-to-br from-background via-primary/5 to-secondary/10">
      <div className="absolute inset-0 overflow-hidden pointer-events-none">
        <div className="absolute top-1/4 left-1/4 w-96 h-96 bg-primary/20 rounded-full blur-3xl animate-float" />
        <div className="absolute bottom-1/4 right-1/4 w-96 h-96 bg-secondary/20 rounded-full blur-3xl animate-float" style={{ animationDelay: "1s" }} />
        <div className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 w-[600px] h-[600px] bg-accent/10 rounded-full blur-3xl animate-float" style={{ animationDelay: "2s" }} />
      </div>

      <Card className="w-full max-w-md shadow-2xl glass-card animate-scale-in relative z-10 border-border/50">
        <CardHeader className="space-y-4 text-center pb-8">
          <div className="flex justify-center mb-4">
            <div className="relative">
              <div className="absolute inset-0 bg-gradient-to-r from-primary via-secondary to-accent rounded-2xl blur-xl opacity-75 animate-pulse" />
              <img
                src="/logo.png"
                alt="Logo MinhaSaúde"
                className="relative w-20 h-auto rounded-xl"
              />
            </div>
          </div>

          <div className="space-y-2 animate-fade-in-up">
            <CardTitle className="text-4xl font-bold font-display gradient-text">
              {isRegister ? "Criar Conta" : "MinhaSaúde"}
            </CardTitle>
            <div className="flex items-center justify-center gap-2 text-muted-foreground">
              <Sparkles className="w-4 h-4 text-accent" />
              <CardDescription className="text-base font-medium">
                {isRegister ? "Faça seu cadastro" : "Sistema de Gestão de Clínicas"}
              </CardDescription>
            </div>
          </div>
        </CardHeader>

        <CardContent className="animate-fade-in-up" style={{ animationDelay: "0.1s" }}>
          <form onSubmit={handleSubmit} className="space-y-5">

            {/* Campo Nome (APENAS NO REGISTRO) */}
            {isRegister && (
              <div className="space-y-2">
                <Label htmlFor="nome" className="text-sm font-medium">Nome</Label>
                <div className="relative group">
                  <User className="absolute left-3 top-1/2 transform -translate-y-1/2 w-5 h-5 text-muted-foreground" />
                  <Input
                    id="nome"
                    placeholder="Digite seu nome"
                    value={nome}
                    onChange={(e) => setNome(e.target.value)}
                    required={isRegister}
                    className="h-12 pl-11"
                  />
                </div>
              </div>
            )}

            {/* Email */}
            <div className="space-y-2">
              <Label htmlFor="email">Email</Label>
              <div className="relative group">
                <User className="absolute left-3 top-1/2 transform -translate-y-1/2 w-5 h-5 text-muted-foreground" />
                <Input
                  id="email"
                  type="email"
                  placeholder="Digite seu email"
                  value={email}
                  onChange={(e) => setEmail(e.target.value)}
                  required
                  className="h-12 pl-11"
                />
              </div>
            </div>

            {/* Senha */}
            <div className="space-y-2">
              <Label htmlFor="senha">Senha</Label>
              <div className="relative group">
                <Lock className="absolute left-3 top-1/2 transform -translate-y-1/2 w-5 h-5 text-muted-foreground" />
                <Input
                  id="senha"
                  type="password"
                  placeholder="Digite sua senha"
                  value={senha}
                  onChange={(e) => setSenha(e.target.value)}
                  required
                  className="h-12 pl-11"
                />
              </div>
            </div>

            <Button
              type="submit"
              className="w-full h-12 font-semibold bg-gradient-to-r from-primary via-secondary to-accent hover:shadow-xl transition-all duration-300"
              disabled={isLoading}
            >
              {isLoading
                ? "Processando..."
                : isRegister
                  ? "Criar Conta"
                  : "Entrar"}
            </Button>
          </form>

          {/* Toggle Login/Register */}
          <div className="text-center mt-4">
            <button
              type="button"
              className="text-primary hover:underline text-sm"
              onClick={() => setIsRegister(!isRegister)}
            >
              {isRegister
                ? "Já tenho conta — Fazer login"
                : "Não tem conta? Criar agora"}
            </button>
          </div>

        </CardContent>
      </Card>
    </div>
  );
};

export default Login;
