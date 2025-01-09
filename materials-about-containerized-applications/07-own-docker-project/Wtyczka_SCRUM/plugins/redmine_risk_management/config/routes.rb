# Główna trasa dla aplikacji redmine_risk_management, wywołująca akcję `index` w kontrolerze `risks`
get '/redmine_risk_management', to: 'risks#index'

# Zasoby dla ryzyk, niezależnie od projektu
get '/all_risks', to: 'risks#all_risks', as: 'all_risks'

# Zasoby dla projektów
resources :projects do
  # Zasoby dla ryzyk w ramach projektów
  resources :risks do
    # Akcja kolekcji `all_risks` - wyświetla wszystkie ryzyka w ramach projektu
    collection do
      get :all_risks  # Trasa do widoku wszystkich ryzyk w projekcie
    end

    # Zasoby dla spike_tasks w ramach ryzyka
    resources :spike_tasks, only: [:create, :show, :update]
  end
  
  # Trasy dla edycji i aktualizacji projektów
  member do
    get :edit
    patch :update
  end
end
