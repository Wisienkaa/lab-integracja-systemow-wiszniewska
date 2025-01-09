module RisksHelper
    def risk_cell_class(likelihood, impact)
      # Zielony (najniższy poziom ryzyka) - Prawdopodobieństwo i wpływ <= 1
      if likelihood <= 1 && impact <= 1
        'low-risk green'
      # Wysokie ryzyko: Prawdopodobieństwo >= 4 lub wpływ >= 4
      elsif (likelihood >= 5 || impact >= 5) || (impact == 4 && likelihood == 4)
        'high-risk'
      # Średnie ryzyko:

      elsif (likelihood == 4 && impact <= 1) || 
            (likelihood == 5 && impact <= 2) || 
            (impact == 5 && likelihood <= 2)
        'medium-risk'
      # Niskie ryzyko: Kombinacje prawdopodobieństwa i wpływu <= 2
      elsif (likelihood <= 2 && impact <= 2) || 
            (likelihood <= 1 && impact <= 3) || 
            (impact <= 1 && likelihood <= 3)
        'low-risk'
      # Pozostałe przypadki jako średnie ryzyko
      else
        'medium-risk'
      end
    end
  end
  